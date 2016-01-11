//require.config({
//    baseUrl: ".",
//    waitSeconds: 60
//});

define([],
    function() {
        angular.module('bookApp.services', []);

        angular.module('bookApp.controllers', [
            'textAngular',
            'pageslide-directive',
            'ngSanitize',
            'toastr',
            'bookApp.services'
        ]);
    });
