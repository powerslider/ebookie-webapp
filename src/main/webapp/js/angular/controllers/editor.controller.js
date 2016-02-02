define([],

    function() {
        angular
            .module('bookApp.controllers')
            .controller('EditorController', EditorController);

        EditorController.$inject = ['$scope', '$sce', '$routeParams'];
        function EditorController($scope, $sce, $routeParams) {
            $scope.htmlContent = 'Table of contents';
            $scope.bookSectionName = $routeParams.tocElementName;
            $scope.sidePanelOpened = false; // This will be binded using the ps-open attribute
            $scope.toggleSidePanel = function () {
                $scope.sidePanelOpened = !$scope.sidePanelOpened;

                console.log($scope.htmlContent);
                $scope.tocHtml = $sce.trustAsHtml($scope.htmlContent);
            };
            //
            //
            //function rec(el) {
            //    return el.map(function() {
            //        var o = $(this).data(),
            //            c = $(this).children('ul, ol').children('li');
            //
            //        if ( c.length > 0 ) o.child = rec(c);
            //        return o
            //    }).get();
            //}
        }
    });

