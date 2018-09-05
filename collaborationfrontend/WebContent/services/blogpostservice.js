/**
 * 
 */
app.factory('BlogPostService',function($http){
	var blogPostService={}
	var BASE_URL="http://localhost:8085/collaborationprojectmiddle"
	
		//QUERY - insert into blogpost values (?,,,) - DAO layer
	blogPostService.addBlogPost=function(blog){
		var url=BASE_URL + "/addblogpost"
		return $http.post(url,blog)
	}
	
	//QUERY - select * from blogpost where approvalstatus=true - IN DAO
	blogPostService.getBlogsApproved=function(){
		return $http.get(BASE_URL + "/approvedblogs")
	}
	
	blogPostService.getBlog=function(id){
		return $http.get(BASE_URL + "/getblog/"+id)
	}
	
	
	return blogPostService;
	
})
