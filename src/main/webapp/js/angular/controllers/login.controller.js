define([],

    function() {
        angular
            .module('bookApp.controllers')
            .controller('LoginController', LoginController);

        LoginController.$inject = ['$scope', '$rootScope', '$location', 'AuthenticationService', 'FlashService', 'toastr'];
        function LoginController($scope, $rootScope, $location, AuthenticationService, FlashService, toastr) {
            $scope.login = login;

            (function initController() {
                 //reset login status
                AuthenticationService.ClearCredentials();
            })();


            function login() {
                $scope.dataLoading = true;
                AuthenticationService.Login($scope.username, $scope.password)
                    .success(function (response) {
                        console.log(response);
                        AuthenticationService.SetCredentials($scope.username, $scope.password);
                        $location.path('/');
                    })
                    .error(function (response) {
                        //toastr.error("ERROR " + response.message);
                        console.log(response);
                        $scope.dataLoading = false;
                    });
            }
        }
    });

