define([],

    function() {
        angular
            .module('bookApp.controllers')
            .controller('RegisterController', RegisterController);

        RegisterController.$inject = ['$scope', 'UserService', '$location', '$rootScope', 'FlashService'];
        function RegisterController($scope, UserService, $location, $rootScope, FlashService) {
            $scope.register = register;

            function register() {
                $scope.dataLoading = true;
                console.log($scope.user);
                UserService.Create($scope.user)
                    .success(function (response) {
                        FlashService.Success(response.data, true);
                        $location.path('/login');
                    })
                    .error(function (response) {
                        FlashService.Error(response.data);
                        $scope.dataLoading = false;
                    });
            }
        }
    });

