angular.module('Employees', ['ngAnimate', 'ngSanitize', 'ui.bootstrap'])
    .controller('EmployeesCtrl', function ($scope, $http) {
        $scope.params = {};

        $scope.getEmployees = function () {
            $http.get('http://localhost:8080/employees?' + $scope.serialize($scope.params)).then(function (response) {
                $scope.employeesTable = response.data;
            });
        };

        $scope.clearDate = function (name) {
            $scope.params[name] = null;
        };

        $scope.serialize = function (obj) {
            var str = [];
            for (var p in obj)
                if (obj.hasOwnProperty(p) && obj[p]) {
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(
                            (obj[p] instanceof Date) ? obj[p].getTime().toString() : obj[p]));
                }
            return str.join("&");
        };

        $scope.getEmployees();
    });
