define([],

    function() {
        angular
            .module('bookApp.controllers')
            .controller('TocEditorController', TocEditorController);

        TocEditorController.$inject = ['$scope', '$sce'];
        function TocEditorController($scope) {
            $scope.htmlContent = 'Table of contents';

            $scope.sidePanelOpened = false; // This will be binded using the ps-open attribute
            $scope.addLinks = function () {
                $('div.toc > ul, li').wrapInner(function() {
                    var link = $('<a/>');
                    link.attr("href", "#/editor/" + $(this).text());
                    link.text($(this).text());
                    $(this).text("");
                    return link;
                });
            };
        }
    });

