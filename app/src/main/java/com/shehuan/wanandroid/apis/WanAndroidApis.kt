package com.shehuan.wanandroid.apis

import com.shehuan.wanandroid.base.net.BaseResponse
import com.shehuan.wanandroid.bean.*
import com.shehuan.wanandroid.bean.article.ArticleBean
import com.shehuan.wanandroid.bean.navi.NaviBean
import com.shehuan.wanandroid.bean.project.ProjectBean
import com.shehuan.wanandroid.bean.chapter.ChapterArticleBean
import com.shehuan.wanandroid.bean.tree.TreeBean
import com.shehuan.wanandroid.bean.treeDetail.TreeDetailBean
import retrofit2.http.*

interface WanAndroidApis {
    /**
     * 登录
     */
    @POST("user/login")
    suspend fun login(@QueryMap param: Map<String, String>): BaseResponse<LoginBean>

    /**
     * 注册
     */
    @POST("user/register")
    suspend fun register(@QueryMap param: Map<String, String>): BaseResponse<RegisterBean>

    /**
     * 退出
     */
    @GET("user/logout/json")
    suspend fun logout(): BaseResponse<String>

    /**
     * 首页banner
     */
    @GET("banner/json")
    suspend fun banner(): BaseResponse<List<BannerBean>>

    /**
     * 常用网站
     */
    @GET("friend/json")
    suspend fun friend(): BaseResponse<List<FriendBean>>

    /**
     * 首页文章列表
     */
    @GET("article/list/{pageNum}/json")
    suspend fun articleList(@Path("pageNum") pageNum: Int): BaseResponse<ArticleBean>

    /**
     * 热词（目前搜索最多的关键词）
     */
    @GET("/hotkey/json")
    suspend fun hotKey(): BaseResponse<List<HotKeyBean>>

    /**
     * 搜索（支持多个关键词，用空格隔开）
     */
    @POST("article/query/{pageNum}/json")
    suspend fun query(@Path("pageNum") pageNum: Int, @Query("k") k: String): BaseResponse<ArticleBean>

    /**
     * 体系结构
     */
    @GET("tree/json")
    suspend fun tree(): BaseResponse<List<TreeBean>>

    /**
     * 体系下的文章
     */
    @GET("article/list/{pageNum}/json")
    suspend fun treeDetail(@Path("pageNum") pageNum: Int, @Query("cid") cid: Int): BaseResponse<TreeDetailBean>

    /**
     * 最新项目
     */
    @GET("article/listproject/{pageNum}/json")
    suspend fun newProject(@Path("pageNum") pageNum: Int): BaseResponse<ProjectBean>

    /**
     * 项目分类
     */
    @GET("project/tree/json")
    suspend fun projectCategory(): BaseResponse<List<ProjectCategoryBean>>


    /**
     * 项目分类详情列表
     */
    @GET("project/list/{pageNum}/json")
    suspend fun projectDetail(@Path("pageNum") pageNum: Int, @Query("cid") cid: Int): BaseResponse<ProjectBean>


    /**
     * 导航
     */
    @GET("navi/json")
    suspend fun nav(): BaseResponse<List<NaviBean>>

    /**
     * 微信公众号列表
     */
    @GET("wxarticle/chapters/json")
    suspend fun chapter(): BaseResponse<List<ChapterBean>>

    /**
     * 微信公众号文章列表
     */
    @GET("wxarticle/list/{chapterId}/{pageNum}/json")
    suspend fun chapterArticleList(@Path("chapterId") chapterId: Int, @Path("pageNum") pageNum: Int): BaseResponse<ChapterArticleBean>

    /**
     * 微信公众号文章搜索
     */
    @GET("wxarticle/list/{chapterId}/{pageNum}/json")
    suspend fun queryChapterArticle(
        @Path("chapterId") chapterId: Int, @Path("pageNum") pageNum: Int, @Query(
            "k"
        ) k: String
    ): BaseResponse<ChapterArticleBean>

    /**
     * 收藏文章列表
     */
    @GET("lg/collect/list/{pageNum}/json")
    suspend fun collectArticleList(@Path("pageNum") pageNum: Int): BaseResponse<ArticleBean>

    /**
     * 收藏站内文章
     */
    @POST("lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): BaseResponse<String>

    /**
     * 在文章列表取消收藏
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun uncollectArticle(@Path("id") id: Int): BaseResponse<String>

    /**
     * 在收藏列表取消收藏
     */
    @POST("lg/uncollect/{id}/json")
    suspend fun cancelMyCollection(@Path("id") id: Int, @Query("originId") originId: Int): BaseResponse<String>
}