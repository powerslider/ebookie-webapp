require.config({
    baseUrl: ".",
    waitSeconds: 60
});

define([/*'webjars/angularjs/1.4.8/angular.min',*/
        'webjars/jquery/2.1.4/jquery.min',
        'webjars/angularjs/1.4.8/angular-animate.min',
        'webjars/angularjs/1.4.8/angular-cookies.min', //ngCookies
        'webjars/angularjs/1.4.8/angular-route',
        //'webjars/textAngular/1.4.1/textAngular-rangy.min',
        //'webjars/textAngular/1.4.1/textAngular-sanitize.min',
        //'webjars/textAngular/1.4.1/textAngular.min',
        //'webjars/rangy/1.3.0/rangy-selectionsaverestore.min',
        //'webjars/rangy/1.3.0/rangy-core.min',
        'webjars/angular-toastr/1.5.0/angular-toastr.min',
        'webjars/bootstrap/4.0.0-alpha/js/bootstrap.min',
        'js/angular/lib/angular-pageslide-directive',
        'js/angular/modules',
        'js/angular/services/user.service',
        'js/angular/services/user.service.local-storage',
        'js/angular/services/authentication.service',
        'js/angular/services/flash.service',
        'js/angular/controllers/editor.controller',
        'js/angular/controllers/home.controller',
        'js/angular/controllers/login.controller',
        'js/angular/controllers/register.controller',
        //'js/angular/controllers',
        //'js/angular/services'
    ],

    function() {
        (function (angular) {
            var bookApp = angular
                .module('bookApp', [
                    'ngRoute',
                    'ngCookies',
                    'bookApp.controllers',
                    'bookApp.services'
                ]);

            bookApp.config(config);
                //.run(run);

            config.$inject = ['$routeProvider'];
            function config($routeProvider) {
                $routeProvider
                    .when('/', {
                        templateUrl: 'partials/home.html',
                        controller: 'HomeController'
                    })
                    .when('/login', {
                        templateUrl: 'partials/login.html',
                        controller: 'LoginController'
                    })
                    .when('/register', {
                        templateUrl: 'partials/register.html',
                        controller: 'RegisterController'
                    })
                    .when('/editor', {
                        templateUrl: 'partials/editor.html',
                        controller: 'EditorController'
                    })
                    .otherwise({redirectTo: '/login'});
            }

            run.$inject = ['$rootScope', '$location', '$cookieStore', '$http'];
            function run($rootScope, $location, $cookieStore, $http) {
                // keep user logged in after page refresh
                $rootScope.globals = $cookieStore.get('globals') || {};
                if ($rootScope.globals.currentUser) {
                    $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
                }

                $rootScope.$on('$locationChangeStart', function (event, next, current) {
                    // redirect to login page if not logged in and trying to access a restricted page
                    var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
                    var loggedIn = $rootScope.globals.currentUser;
                    if (restrictedPage && !loggedIn) {
                        $location.path('/login');
                    }
                });
            }

            // Bootstrap Application
            angular.element(document).ready(function () {
                angular.bootstrap(document, ['bookApp']);
            });
        })(angular)
    });
