package com.example.myboard_jwt.service;

import com.example.myboard_jwt.dto.PostDto;
import com.example.myboard_jwt.dto.PostResponseDto;
import com.example.myboard_jwt.entity.Likes;
import com.example.myboard_jwt.entity.Post;
import com.example.myboard_jwt.entity.User;
import com.example.myboard_jwt.eventHandler.FileDeleteEvent;
import com.example.myboard_jwt.eventHandler.FileRecoverEvent;
import com.example.myboard_jwt.exception.PblException;
import com.example.myboard_jwt.repository.LikeRepository;
import com.example.myboard_jwt.repository.PostRepository;
import com.example.myboard_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.myboard_jwt.exception.ErrorConstant.DEFAULT_ERROR;
import static com.example.myboard_jwt.exception.ErrorConstant.FILE_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final FileHandler fileHandler;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * create Board Service
     */
    @Transactional
    public PostResponseDto createPost(PostDto.FileReq createReq, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new PblException("not exist member", DEFAULT_ERROR));

        //파일 경로 추출
        String filePath = fileHandler.getFilePath(createReq.getPicture());
        //db저장
        Post newPost = postRepository.save(Post.createPost(createReq.getContent(), filePath, user));
        //파일 저장 - db와 파일은 시스템이 다르니 수동으로 트랜잭션처리 해주어야 한다.
        try {
            fileHandler.saveFile(createReq.getPicture(), filePath);
        } catch (Exception e) {
            log.info("파일 생성 에러 파일도 롤백(삭제) 시도");
            eventPublisher.publishEvent(new FileDeleteEvent(filePath));   //파일 업로드시 에러가 나면 파일 삭제 시도
            throw new PblException("파일 생성 에러 -> 롤백시도", DEFAULT_ERROR);               //db rollback
        }
        byte[] pathToByte = fileHandler.getFileToByte(filePath);
        return new PostResponseDto(newPost, pathToByte);
    }

    public PostDto.PostResList getBoardList(Long userId, Pageable pageable) {
        Slice<Post> Posts = postRepository.findAllByOrderByLikeCountDesc(pageable);
        //내가 좋아요 누른 Board 아이디 리스트
        List<Likes> userLikeList = likeRepository.findByUserIdWithPost(userId);
        List<Long> likePostId = userLikeList.stream().map(l -> l.getPost().getId()).collect(Collectors.toList());

        Slice<PostDto.PostRes> postResList = Posts.map(p ->
                new PostDto.PostRes(
                        p.getId(),
                        p.getUser().getNickname(),
                        p.getContent(),
                        fileHandler.getFileToByte(p.getPicture()),
                        p.getLikeCount(),
                        likePostId.stream().anyMatch(postId -> postId.equals(p.getId())),
                        p.getModifiedAt()
                ));

        return new PostDto.PostResList(postResList);

    }

    public PostDto.PostRes getPosts(Long postId, Long memberId) {
        Optional<Post> post = postRepository.findById(postId);
        List<Likes> userLikeList = memberId != null ? likeRepository.findByUserIdWithPost(memberId) : new ArrayList<>();
        Stream<Long> likePostId = userLikeList.stream().map(l -> l.getPost().getId());

        return post.map(p ->
                new PostDto.PostRes(
                        p.getId(),
                        p.getUser().getNickname(),
                        p.getContent(),
                        fileHandler.getFileToByte(p.getPicture()),
                        p.getLikeCount(),
                        likePostId.anyMatch(boardId -> boardId.equals(p.getId())),
                        p.getModifiedAt()
                )).orElseThrow(() -> new PblException("존재하지 않는 개시판입니다.", DEFAULT_ERROR));
    }

    @Transactional
    public PostResponseDto updatePost(Long postId, Long userId, PostDto.FileReq fileReq) {
        //자신의 보드가 맞는지 확인 후 수정
        Post post = postRepository.findById(postId).filter(b -> b.getUser().getId().equals(userId))
                .orElseThrow(() -> new PblException("게시판 주인이 아닙니다.", DEFAULT_ERROR));

        byte[] backupImage = fileHandler.getFileToByte(post.getPicture());
        String backupPath = post.getPicture();

        String filePath = fileHandler.getFilePath(fileReq.getPicture());
        post.update(fileReq.getContent(), filePath);
        try {
            fileHandler.removeFile(backupPath);
            fileHandler.saveFile(fileReq.getPicture(), filePath);  //자신거 삭제 & 기존 파일 복원
        } catch (Exception e){
            eventPublisher.publishEvent(new FileRecoverEvent(backupPath, backupImage));    //복원
            eventPublisher.publishEvent(new FileDeleteEvent(filePath));                   //삭제
            throw new PblException("파일 저장 에러", FILE_ERROR);
        }
        byte[] pathToByte = fileHandler.getFileToByte(filePath);
        return new PostResponseDto(post, pathToByte);
    }

    @Transactional
    public void removePost(Long postId, Long userId) {
        postRepository.findById(postId)
                .filter(post -> post.getUser().getId().equals(userId))
                .ifPresentOrElse(
                        (post) -> {
                            postRepository.delete(post);
                            fileHandler.removeFile(post.getPicture());
                        },
                        () -> {throw new PblException("본인이 올린 글만 삭제할 수 있습니다.", DEFAULT_ERROR);}
                );
    }

    public Slice<PostDto.PostRes> getpaging(Pageable pageable) {

        Slice<Post> post = postRepository.findAllByOrderByLikeCountDesc(pageable);

        Slice<PostDto.PostRes> map = post.map(p ->
                new PostDto.PostRes(
                        p.getId(),
                        p.getUser().getNickname(),
                        p.getContent(),
                        fileHandler.getFileToByte(p.getPicture()),
                        p.getLikeCount(),
                        false,
                        p.getModifiedAt()
                ));
        return map;
    }
}