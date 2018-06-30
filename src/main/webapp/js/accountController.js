app.controller('Accounts', ['$scope', '$http', function ($scope, $http) {
    'use strict';
    $http.get('/rest/accounts').success(function(res) {
        $scope.accounts = res;
    });
}]);