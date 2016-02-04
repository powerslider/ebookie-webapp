define([],

    function() {

        angular
            .module('bookApp.services')
            .factory('EbookService', EbookService);

        EbookService.$inject = ['$http'];
        function EbookService($http) {
            var service = {};

            service.Create = Create;

            return service;

            function Create(ebook) {
                var headers =  {
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    }
                };
                return $http.post('api/ebooks/new', ebook, headers);
            }

            // private functions

            function handleSuccess(res) {
                return res.data;
            }

            function handleError(error) {
                return function () {
                    return {success: false, message: error};
                };
            }
        }
    });

