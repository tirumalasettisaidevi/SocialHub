/**
 * BlogInDetailCtrl
 * #/getblog/id
 */
app.controller('BlogInDetailCtrl',function($scope,$location,BlogPostService,$routeParams,$sce){
	var id=$routeParams.id
	
	//statement //select * from blogpost where id=?
	BlogPostService.getBlog(id).then(function(response){
		$scope.blogPost=response.data//BlogPost object
		$scope.content=$sce.trustAsHtml($scope.blogPost.blogContent)
	},function(response){
		if(response.status==401)
			$location.path('/login')
	})
	
})

