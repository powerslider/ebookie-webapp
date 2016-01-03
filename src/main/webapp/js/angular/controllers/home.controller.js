define([],

    function() {
        angular
            .module('bookApp.controllers')
            .controller('HomeController', HomeController);

        HomeController.$inject = ['$scope', '$rootScope', 'UserService'];
        function HomeController($scope, $rootScope, UserService) {
            $scope.message = "My Message Is Super Awesome";

            $scope.user = null;
            $scope.allUsers = [];
            //$scope.deleteUser = deleteUser;

            $scope.sidePanelOpened = false; // This will be binded using the ps-open attribute
            $scope.toggleSidePanel = function () {
                $scope.sidePanelOpened = !$scope.sidePanelOpened;
            };

            //initController();
            //
            //function initController() {
            //    loadCurrentUser();
            //    loadAllUsers();
            //}
            //
            //function loadCurrentUser() {
            //    UserService.GetByUsername($rootScope.globals.currentUser.username)
            //        .then(function (user) {
            //            $scope.user = user;
            //        });
            //}
            //
            //function loadAllUsers() {
            //    UserService.GetAll()
            //        .then(function (users) {
            //            $scope.allUsers = users;
            //        });
            //}

            //function deleteUser(id) {
            //    UserService.Delete(id)
            //        .then(function () {
            //            loadAllUsers();
            //        });
            //}
        }
    });

