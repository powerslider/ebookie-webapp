define([],

    function() {
        angular
            .module('bookApp.controllers')
            .controller('RegisterController', RegisterController);

        RegisterController.$inject = ['$scope, UserService', '$location', '$rootScope', 'FlashService'];
        function RegisterController($scope, UserService, $location, $rootScope, FlashService) {
            $scope.register = register;

            function register() {
                $scope.dataLoading = true;
                UserService.Create($scope.user)
                    .then(function (response) {
                        if (response.success) {
                            FlashService.Success('Registration successful', true);
                            $location.path('/login');
                        } else {
                            FlashService.Error(response.message);
                            $scope.dataLoading = false;
                        }
                    });
            }
        }
    });

