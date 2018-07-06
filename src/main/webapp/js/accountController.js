app.controller('Accounts', ['$scope', '$http', function ($scope, $http) {
    $http.get('/rest/accounts').success(function(response) {
        $scope.accounts = response;
    });
}]);

app.controller('postController', function($scope, $http) {
    $scope.submitForm = function() {
        $http({
            method  : 'POST',
            url     : '/rest/accounts',
            data    : $scope.account,
            headers : { 'Content-Type': 'application/json' } 
        }).success(function(response) {
            $scope.message = "Added: " + response.name;
        }).error(function(response) {
            $scope.message = "Something wrong";
        });
    };
});