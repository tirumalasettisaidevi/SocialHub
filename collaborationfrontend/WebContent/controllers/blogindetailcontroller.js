/**
 * BlogInDetailCtrl
 * #/getblog/id
 */
app.controller('BlogInDetailCtrl',function($scope,$location,BlogPostService,$routeParams,$sce){
	var id=$routeParams.id
	$scope.isRejected=false
	//statement //select * from blogpost where id=?
	BlogPostService.getBlog(id).then(function(response){
		$scope.blogPost=response.data//BlogPost object
		$scope.content=$sce.trustAsHtml($scope.blogPost.blogContent)
	},function(response){
		if(response.status==401)
			$location.path('/login')
	})
	
	$scope.approveBlogPost=function(blogPost){
		BlogPostService.approveBlogPost(blogPost).then(function(response){
			$location.path('/blogswaitingforapproval')
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
    
	$scope.rejectBlogPost=function(blogPost){
		console.log(blogPost)
		BlogPostService.rejectBlogPost(blogPost,$scope.rejectionReason).then(function(response){
			$location.path('/blogswaitingforapproval')
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	$scope.showTextArea=function(){
		$scope.isRejected=!$scope.isRejected
	}
	
})

