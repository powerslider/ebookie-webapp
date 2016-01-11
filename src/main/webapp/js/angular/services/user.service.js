define([],

    function() {

        angular
            .module('bookApp.services')
            .factory('UserService', UserService);

        UserService.$inject = ['$http'];
        function UserService($http) {
            var service = {};

            service.GetAll = GetAll;
            service.GetById = GetById;
            service.GetByUsername = GetByUsername;
            service.Create = Create;
            service.Update = Update;

            return service;

            function GetAll() {
                return $http.get('api/users')
                    .then(handleSuccess, handleError('Error getting all users'));
            }

            function GetById(id) {
                return $http.get('api/users/' + id)
                    .then(handleSuccess, handleError('Error getting user by id'));
            }

            function GetByUsername(username) {
                return $http.get('api/users/' + username)
                    .then(handleSuccess, handleError('Error getting user by username'));
            }

            function Create(user) {
                var headers =  {
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    }
                };
                return $http.post('api/users/register', user, headers);
            }

            function Update(user) {
                return $http.put('api/users/update' + user.id, user)
                    .then(handleSuccess, handleError('Error updating user'));
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

