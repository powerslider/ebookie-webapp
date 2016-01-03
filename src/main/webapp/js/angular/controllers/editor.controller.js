define([],

    function() {
        angular
            .module('bookApp.controllers')
            .controller('EditorController', EditorController);

        EditorController.$inject = ['$scope'];
        function EditorController($scope) {
            $scope.htmlContent = 'Table of contents';
        }
    });

