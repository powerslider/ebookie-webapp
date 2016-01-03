define([],

    function() {
        var bookAppControllers = angular
            .module('bookApp.controllers', []);

        HomeController.$inject = ['$scope', '$rootScope', 'UserService'];
        function HomeController($scope, $rootScope, UserService) {
            $scope.message = "My Message Is Super Awesome";
            console.log("eeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            $scope.user = null;

            $scope.allUsers = [];
            $scope.deleteUser = deleteUser;
            initController();


            function initController() {
                loadCurrentUser();
                loadAllUsers();
            }

            function loadCurrentUser() {
                UserService.GetByUsername($rootScope.globals.currentUser.username)
                    .then(function (user) {
                        $scope.user = user;
                    });
            }

            function loadAllUsers() {
                UserService.GetAll()
                    .then(function (users) {
                        $scope.allUsers = users;
                    });
            }

            function deleteUser(id) {
                UserService.Delete(id)
                    .then(function () {
                        loadAllUsers();
                    });
            }
        }

        LoginController.$inject = ['$scope', '$location', 'AuthenticationService', 'FlashService'];
        function LoginController($scope, $location, AuthenticationService, FlashService) {
            $scope.login = login;

            (function initController() {
                //reset login status
                AuthenticationService.ClearCredentials();
            })();

            function login() {
                $scope.dataLoading = true;
                AuthenticationService.Login($scope.username, $scope.password, function (response) {
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

        EditorController.$inject = ['$scope'];
        function EditorController($scope) {
            //$scope.register = register;
            //
            //function register() {
            //    $scope.dataLoading = true;
            //    UserService.Create($scope.user)
            //        .then(function (response) {
            //            if (response.success) {
            //                FlashService.Success('Registration successful', true);
            //                $location.path('/login');
            //            } else {
            //                FlashService.Error(response.message);
            //                $scope.dataLoading = false;
            //            }
            //        });
            //}
        }

        bookAppControllers
            .controller('HomeController', HomeController)
            .controller('LoginController', LoginController)
            .controller('RegisterController', RegisterController)
            .controller('EditorController', EditorController);

        return bookAppControllers;

    });