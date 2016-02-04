define([],

    function() {
        angular
            .module('bookApp.controllers')
            .controller('HomeController', HomeController);

        HomeController.$inject = ['$scope', '$rootScope', 'UserService'];
        function HomeController($scope, $rootScope, UserService) {
            $scope.sidePanelOpened = false; // This will be binded using the ps-open attribute
            $scope.toggleSidePanel = function () {
                $scope.sidePanelOpened = !$scope.sidePanelOpened;
            };

            var loggedUserUsername = $rootScope.globals.currentUser.username;
            UserService.GetByUsername(loggedUserUsername)
                .then(function (response) {
                    console.log(response);
                    $scope.user = response;
                }, function (response) {
                    console.log("ERROR " + response);
                });
        }
    });

