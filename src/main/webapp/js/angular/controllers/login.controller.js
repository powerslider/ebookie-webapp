define([],

    function() {
        angular
            .module('bookApp.controllers')
            .controller('LoginController', LoginController);

        LoginController.$inject = ['$scope', '$location', 'AuthenticationService', 'FlashService'];
        function LoginController($scope, $location, AuthenticationService, FlashService) {
            $scope.login = login;

            (function initController() {
                 //reset login status
                AuthenticationService.ClearCredentials();
            })();

            function login() {
                $scope.dataLoading = true;
                AuthenticationService.Login(vm.username, vm.password, function (response) {
                    if (response.success) {
                        AuthenticationService.SetCredentials($scope.username, $scope.password);
                        $location.path('/');
                    } else {
                        FlashService.Error(response.message);
                        $scope.dataLoading = false;
                    }
                });
            }
        }
    });

