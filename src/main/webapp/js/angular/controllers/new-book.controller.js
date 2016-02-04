define([],

    function() {
        angular
            .module('bookApp.controllers')
            .controller('NewBookController', NewBookController);

        NewBookController.$inject = ['$scope', '$rootScope', '$sce', 'EbookService', 'UserService', 'toastr'];
        function NewBookController($scope, $rootScope, $sce, EbookService, UserService, toastr) {
            $scope.htmlContent = 'Table of contents';

            $scope.sidePanelOpened = false; // This will be binded using the ps-open attribute
            $scope.isBookCreated = false;

            $scope.saveNewBook = saveNewBook;
            $scope.addLinks = addLinks;

            var loggedUserUsername = $rootScope.globals.currentUser.username;
            UserService.GetByUsername(loggedUserUsername)
                .then(function (response) {
                    console.log(response);
                    $scope.currentUser = response;
                }, function (response) {
                    console.log("ERROR " + response);
                });

            function saveNewBook() {
                addLinks();
                $scope.dataLoading = true;
                $scope.ebook.tableOfContents = $scope.htmlContent;
                $scope.ebook.author = $scope.currentUser;
                console.log($scope.ebook);
                EbookService.Create($scope.ebook)
                    .success(function (response) {
                        $scope.isBookCreated = true;
                        $scope.dataLoading = false;
                        //toastr.success("aaeuaeuae");
                    })
                    .error(function (response) {
                        $scope.dataLoading = false;
                    });
            }

            function addLinks() {
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

