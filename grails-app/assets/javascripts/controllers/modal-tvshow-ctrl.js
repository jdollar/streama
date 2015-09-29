'use strict';

streamaApp.controller('modalTvShowCtrl', [
	'$scope', '$modalInstance', 'apiService', 'tvShow',
	function ($scope, $modalInstance, apiService, tvShow) {


	$scope.loading = false;
	$scope.tvShow = tvShow || {};

	$scope.saveShow = function (video) {
		apiService.tvShow.save(video).success(function (data) {
			$modalInstance.close(data);
		});
	};

	$scope.selectFromAPI = function ($item) {
		var apiId = $item.id;
		delete $item.id;
		$scope.tvShow = $item;
		$scope.tvShow.apiId = apiId;
        $scope.addManually = false;
	};


	$scope.search = function (query) {
		return apiService.theMovieDb.search('tv', query).then(function (data) {
			return data.data;
		});
	};

    $scope.manualAdd = function(tvShow) {
        $scope.tvShow = {'name': tvShow.name, 'visibleDetails': true};
        $scope.addManually = true; //disables api_id input
    }

    $scope.visibleDetails = function(tvShow) {
        return tvShow ? (tvShow.visibleDetails || tvShow.overview) : false
    }

	setTimeout(function () {
		$('.name-input').focus();
	}, 200);


	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
}]);
