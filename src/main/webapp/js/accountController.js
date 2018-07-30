app.controller('Accounts', ['$scope', '$http', function ($scope, $http) {
    $http({
        method  : 'GET',
        url     : '/rest/accounts',
        }).success(function(response) {
        $scope.accounts = response;
    });
}]);

app.controller('postController', function($scope, $http) {
    $scope.submitForm = function() {
        $scope.account.balance = currency($scope.account.balance).value;
        $scope.account.creditLimit = currency($scope.account.creditLimit).value;
        $http({
            method  : 'POST',
            url     : '/rest/accounts',
            data    : $scope.account,
            headers : { 'Content-Type': 'application/json' } 
        }).success(function(response) {
            $scope.successMessage = true;
            $scope.message = "Added: " + response.name;
            $scope.account = {};
        }).error(function(response) {
            $scope.errorMessage = true;
            $scope.message = "Something wrong";
        });
    };
});