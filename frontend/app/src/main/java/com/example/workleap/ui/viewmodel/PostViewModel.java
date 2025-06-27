package com.example.workleap.ui.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workleap.data.model.entity.Comment;
import com.example.workleap.data.model.entity.Post;
import com.example.workleap.data.model.entity.Reaction;
import com.example.workleap.data.model.response.CommentResponse;
import com.example.workleap.data.model.response.ImageUrlResponse;
import com.example.workleap.data.model.response.ListCommentResponse;
import com.example.workleap.data.model.response.PostResponse;
import com.example.workleap.data.model.response.ListPostResponse;
import com.example.workleap.data.model.response.MessageResponse;
import com.example.workleap.data.model.response.ReactionResponse;
import com.example.workleap.data.repository.PostRepository;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends ViewModel {
    private PostRepository postRepository;

    private MutableLiveData<List<Post>> getAllPostData = new MutableLiveData<>();
    private MutableLiveData<List<Post>> getPostCompanyData = new MutableLiveData<>();
    private MutableLiveData<Post> getPostByIdData = new MutableLiveData<>();
    private MutableLiveData<String> getAllPostResult = new MutableLiveData<>();
    private MutableLiveData<String> getPostCompanyResult = new MutableLiveData<>();
    private MutableLiveData<String> createPostResult = new MutableLiveData<>();
    private MutableLiveData<Post> createPostData = new MutableLiveData<>();
    private MutableLiveData<String> deleteAllPostResult = new MutableLiveData<>();
    private MutableLiveData<String> deletePostByIdResult = new MutableLiveData<>();
    private MutableLiveData<String> getPostByIdResult = new MutableLiveData<>();
    private MutableLiveData<String> updatePostByIdResult = new MutableLiveData<>();

    private MutableLiveData<String> creatCommentResult = new MutableLiveData<>();
    private MutableLiveData<Comment> creatCommentData = new MutableLiveData<>();
    private MutableLiveData<String> getCommentByPostResult = new MutableLiveData<>();
    private MutableLiveData<List<Comment>> getCommentByPostData = new MutableLiveData<>();
    private MutableLiveData<String> deleteCommentByIdResult = new MutableLiveData<>();

    private MutableLiveData<String> toggleReactionResult = new MutableLiveData<>();
    private MutableLiveData<String> removeReactionResult = new MutableLiveData<>();

    private MutableLiveData<String> uploadImageResult = new MutableLiveData<>();
    private MutableLiveData<String> getImageUrlData = new MutableLiveData<>();
    private MutableLiveData<String> getImageUrlResult = new MutableLiveData<>();
    private MutableLiveData<Map<String, String>> imageUrlMap = new MutableLiveData<>(new HashMap<>());

    private MutableLiveData<List<Post>> searchPostData = new MutableLiveData<>();
    private MutableLiveData<List<Post>> getPostByStatusData = new MutableLiveData<>();
    private MutableLiveData<String> searchPostResult = new MutableLiveData<>();
    private MutableLiveData<String> getPostByStatusResult = new MutableLiveData<>();

    public PostViewModel(){}
    public void InitiateRepository(Context context) {
        postRepository = new PostRepository(context);
    }

    // Getter cho LiveData
    public LiveData<List<Post>> getAllPostData() { return getAllPostData; }
    public LiveData<List<Post>> getPostCompanyData() { return getPostCompanyData; }
    public LiveData<Post> getPostByIdData() { return getPostByIdData; }
    public LiveData<String> getAllPostResult() { return getAllPostResult; }
    public LiveData<String> getPostCompanyResult() { return getPostCompanyResult; }
    public LiveData<String> createPostResult() { return createPostResult; }
    public LiveData<Post> createPostData() { return createPostData; }
    public LiveData<String> deleteAllPostResult() { return deleteAllPostResult; }
    public LiveData<String> deletePostByIdResult() { return deletePostByIdResult; }
    public LiveData<String> getPostByIdResult() { return getPostByIdResult; }
    public LiveData<String> updatePostByIdResult() { return updatePostByIdResult; }

    //comment
    public LiveData<String> creatCommentResult() { return creatCommentResult; }
    public LiveData<Comment> creatCommentData() { return creatCommentData; }
    public LiveData<String> getCommentByPostResult() { return getCommentByPostResult; }
    public LiveData<List<Comment>> getCommentByPostData() { return getCommentByPostData; }
    public LiveData<String> deleteCommentByIdResult() { return deleteCommentByIdResult; }

    //reaction
    public LiveData<String> toggleReactionResult() { return toggleReactionResult; }
    public LiveData<String> removeReactionResult() { return removeReactionResult; }

    //image
    public LiveData<String> uploadImageResult() { return uploadImageResult; }
    public LiveData<String> getImageUrlResult() { return getImageUrlResult; }
    public LiveData<String> getImageUrlData() { return getImageUrlData; }
    public LiveData<Map<String, String>> getImageUrlMap() {
        return imageUrlMap;
    }

    //search
    public LiveData<List<Post>> searchPostData() { return searchPostData; }
    public LiveData<List<Post>> getPostByStatusData() { return getPostByStatusData; }
    public LiveData<String> searchPostResult() { return searchPostResult; }
    public LiveData<String> getPostByStatusResult() { return getPostByStatusResult; }

    // Get all post
    public void getAllPost(int page, int pageSize) {
        Call<ListPostResponse> call = postRepository.getAllPosts(page, pageSize);
        call.enqueue(new Callback<ListPostResponse>() {
            @Override
            public void onResponse(Call<ListPostResponse> call, Response<ListPostResponse> response) {
                if (response.isSuccessful()) {
                    ListPostResponse listPostResponse = response.body();
                    Log.d("getAllPost", new Gson().toJson(listPostResponse));
                    getAllPostData.setValue(listPostResponse.getAllPost());
                    getAllPostResult.setValue("Lấy danh sách Post thành công");
                } else {
                    try {
                        ListPostResponse error = new Gson().fromJson(response.errorBody().string(), ListPostResponse.class);
                        getAllPostResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getAllPostResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ListPostResponse> call, Throwable t) {
                getAllPostResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    // Get post company
    public void getPostByCompany(String companyId, int page, int pageSize) {
        Call<ListPostResponse> call = postRepository.getPostsByCompany(companyId, page, pageSize);
        call.enqueue(new Callback<ListPostResponse>() {
            @Override
            public void onResponse(Call<ListPostResponse> call, Response<ListPostResponse> response) {
                if (response.isSuccessful()) {
                    ListPostResponse listPostResponse = response.body();
                    Log.d("GET POST COMPANY", new Gson().toJson(response.body()));
                    getPostCompanyData.setValue(listPostResponse.getAllPost());
                    getPostCompanyResult.setValue("Lấy danh sách Post company thành công");
                } else {
                    try {
                        ListPostResponse error = new Gson().fromJson(response.errorBody().string(), ListPostResponse.class);
                        getPostCompanyResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getPostCompanyResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ListPostResponse> call, Throwable t) {
                getPostCompanyResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Create post
    public void createPost(Post post) {
        Call<PostResponse> call = postRepository.createPost(post);
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    createPostResult.setValue("Create post success");
                    PostResponse postResponse = response.body();
                    createPostData.setValue(postResponse.getPost());
                } else {
                    try {
                        PostResponse error = new Gson().fromJson(response.errorBody().string(), PostResponse.class);
                        createPostResult.setValue("Lỗi: " + error.getMessage());
                        Log.d("API_RESPONSE_CREATEPOST", new Gson().toJson(response.errorBody()));
                    } catch (Exception e) {
                        createPostResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                createPostResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //get post by id
    public void getPostById(String id) {
        Call<PostResponse> call = postRepository.getPostById(id);
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    PostResponse postResponse = response.body();
                    getPostByIdData.setValue(postResponse.getPost());
                    getPostByIdResult.setValue(postResponse.getMessage());
                } else {
                    try {
                        PostResponse error = new Gson().fromJson(response.errorBody().string(), PostResponse.class);
                        getPostByIdResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getPostByIdResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                getPostByIdResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });

    }

    //update post by id
    public void updatePostById(String id, Post request) {
        Call<PostResponse> call = postRepository.updatePost(id, request);
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    PostResponse postResponse = response.body();
                    updatePostByIdResult.setValue("Update post success");
                } else {
                    try {
                        PostResponse error = new Gson().fromJson(response.errorBody().string(), PostResponse.class);
                        updatePostByIdResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        updatePostByIdResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                updatePostByIdResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //delete by id
    public void deletePostById(String id) {
        Call<MessageResponse> call = postRepository.deletePost(id);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse postResponse = response.body();
                    //deletePostByIdResult.setValue(postResponse.getMessage());
                    deletePostByIdResult.setValue("successful");
                } else {
                    try {
                        PostResponse error = new Gson().fromJson(response.errorBody().string(), PostResponse.class);
                        deletePostByIdResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        deletePostByIdResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }
            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                deletePostByIdResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Create comment
    public void createComment(Comment comment) {
        Call<CommentResponse> call = postRepository.createComment(comment);
        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if (response.isSuccessful()) {
                    creatCommentResult.setValue("Create comment success");
                    Comment newComment = response.body().getComment();
                    creatCommentData.setValue(newComment);
                } else {
                    try {
                        CommentResponse error = new Gson().fromJson(response.errorBody().string(), CommentResponse.class);
                        creatCommentResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        creatCommentResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                creatCommentResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Get comment of post
    public void getCommentByPost(String postId) {
        Call<ListCommentResponse> call = postRepository.getCommentsByPost(postId);
        call.enqueue(new Callback<ListCommentResponse>() {
            @Override
            public void onResponse(Call<ListCommentResponse> call, Response<ListCommentResponse> response) {
                if (response.isSuccessful()) {
                    getCommentByPostData.setValue(response.body().getAllComment());
                    Log.d("API_RESPONSE_GETCOMMENT", new Gson().toJson(response.body()));
                    getCommentByPostResult.setValue("Get comments success");
                } else {
                    try {
                        ListCommentResponse error = new Gson().fromJson(response.errorBody().string(), ListCommentResponse.class);
                        getCommentByPostResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getCommentByPostResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ListCommentResponse> call, Throwable t) {
                getCommentByPostResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Delete comment
    public void deleteCommentById(String id) {
        Call<MessageResponse> call = postRepository.deleteComment(id);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    deleteCommentByIdResult.setValue("Delete comment success");
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        deleteCommentByIdResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        deleteCommentByIdResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                deleteCommentByIdResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }


    //Reaction
    //toggle reaction
    public void toggleReaction(Reaction request) {
        Call<ReactionResponse> call = postRepository.toggleReaction(request);
        call.enqueue(new Callback<ReactionResponse>() {
            @Override
            public void onResponse(Call<ReactionResponse> call, Response<ReactionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                } else {
                    try {
                        ReactionResponse error = new Gson().fromJson(response.errorBody().string(), ReactionResponse.class);
                        toggleReactionResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        toggleReactionResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ReactionResponse> call, Throwable t) {
                toggleReactionResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Remove reaction
    public void removeReaction(String id) {
        Call<MessageResponse> call = postRepository.removeReaction(id);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    removeReactionResult.setValue("Remove reaction success");
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        removeReactionResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        removeReactionResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                removeReactionResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //upload image
    public void uploadImage(MultipartBody.Part file, RequestBody postId, RequestBody order) {
        Log.d("uploadImage", "uploadImage is called");
        Call<MessageResponse> call = postRepository.uploadImage(file, postId, order);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    uploadImageResult.setValue("Upload image success");
                } else {
                    try {
                        MessageResponse error = new Gson().fromJson(response.errorBody().string(), MessageResponse.class);
                        uploadImageResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        uploadImageResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                uploadImageResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void getImageUrl(String filePath) {
        Call<ImageUrlResponse> call = postRepository.getImageUrl(filePath);
        call.enqueue(new Callback<ImageUrlResponse>() {
            @Override
            public void onResponse(Call<ImageUrlResponse> call, Response<ImageUrlResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String url = response.body().getUrl();

                    Map<String, String> currentMap = imageUrlMap.getValue();
                    if (currentMap != null) {
                        currentMap.put(filePath, url);
                        imageUrlMap.postValue(new HashMap<>(currentMap)); // post bản copy mới
                    }

                    getImageUrlResult.setValue("Get url image success");
                } else {
                    try {
                        ImageUrlResponse error = new Gson().fromJson(response.errorBody().string(), ImageUrlResponse.class);
                        getImageUrlResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getImageUrlResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ImageUrlResponse> call, Throwable t) {
                getImageUrlResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    //Search
    public void searchPosts(String title, String companyName) {
        Call<ListPostResponse> call = postRepository.searchPosts(title, companyName);
        call.enqueue(new Callback<ListPostResponse>() {
            @Override
            public void onResponse(Call<ListPostResponse> call, Response<ListPostResponse> response) {
                if (response.isSuccessful()) {
                    ListPostResponse listPostResponse = response.body();
                    searchPostData.setValue(listPostResponse.getAllPost());
                    searchPostResult.setValue("Tìm danh sách Post thành công");
                } else {
                    try {
                        ListPostResponse error = new Gson().fromJson(response.errorBody().string(), ListPostResponse.class);
                        searchPostResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        searchPostResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ListPostResponse> call, Throwable t) {
                searchPostResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }

    public void getPostByStatus(int page, int pageSize, String status) {
        Call<ListPostResponse> call = postRepository.getPostByStatus(page, pageSize, status);
        call.enqueue(new Callback<ListPostResponse>() {
            @Override
            public void onResponse(Call<ListPostResponse> call, Response<ListPostResponse> response) {
                if (response.isSuccessful()) {
                    ListPostResponse listPostResponse = response.body();
                    getPostByStatusData.setValue(listPostResponse.getAllPost());
                    getPostByStatusResult.setValue("Success");
                } else {
                    try {
                        ListPostResponse error = new Gson().fromJson(response.errorBody().string(), ListPostResponse.class);
                        getPostByStatusResult.setValue("Lỗi: " + error.getMessage());
                    } catch (Exception e) {
                        getPostByStatusResult.setValue("Lỗi không xác định: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ListPostResponse> call, Throwable t) {
                getPostByStatusResult.setValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}
