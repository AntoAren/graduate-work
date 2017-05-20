'use strict';

angular.module('itest.portal.routing', ['ui.router'])
    .run(['$rootScope', '$state', function ($rootScope, $state) {
        var currentStateName = $state.current.name;

        $rootScope.$state = $state;
        $rootScope.$on('$stateChangeSuccess', function () {
            $rootScope.previousStateName = currentStateName;
            currentStateName = $state.current.name;
        });
    }
    ])
    .config(function ($stateProvider, $locationProvider, $urlRouterProvider) {
        var i;

        var states = [
            {
                title: 'Login',
                name: 'login',
                url: '/login',
                reloadOnSearch: false,
                controller: 'LoginCtrl',
                templateUrl: 'views/auth/login.html'
            },
            {
                title: 'Logout',
                name: 'logout',
                url: '/logout',
                controller: 'LogoutCtrl'
            },
            {
                title: 'Sign Up',
                name: 'signup',
                url: '/signup',
                controller: 'SignupCtrl',
                templateUrl: 'views/auth/signup.html'
            },
            {
                title: 'Главная страница',
                name: 'home',
                url: '/',
                onEnter: ['$state', function ($state) {
                    $state.go('alltests', {}, {location: 'replace'});
                }]
            },
            {
                title: 'Marketplace',
                name: 'marketplace',
                abstract: true,
                url: '/marketplace',
                templateUrl: 'views/common/navigation.html',
                controller: 'MarketplaceCtrl'
            },
            {
                title: 'Все тесты',
                name: 'alltests',
                url: '/all-tests?category&topic&search&sort',
                reloadOnSearch: true,
                templateUrl: 'views/alltests/alltests.html',
                controller: 'AllTestsCtrl'
            },
            {
                title: 'Назначенные мне',
                name: 'assignedtome',
                url: '/assigned-to-me?category&topic&search&sort',
                reloadOnSearch: true,
                templateUrl: 'views/assignedtome/assignedtome.html',
                controller: 'AssignedToMeCtrl'
            },
            {
                title: 'Мои результаты',
                name: 'myresults',
                url: '/my-results?category&topic&search&sort',
                reloadOnSearch: true,
                templateUrl: 'views/myresults/myresults.html',
                controller: 'MyResultsCtrl'
            },
            {
                title: 'Созданные мной',
                name: 'createdbyme',
                url: '/created-by-me?category&topic&search&sort',
                reloadOnSearch: true,
                templateUrl: 'views/createdbyme/createdbyme.html',
                controller: 'CreatedByMeCtrl'
            },
            {
                title: 'Создание нового теста',
                name: 'createnewtest',
                url: '/create-new-test',
                reloadOnSearch: true,
                templateUrl: 'views/createnewtest/createnewtest.html',
                controller: 'CreateNewTestCtrl'
            },
            {
                title: 'Изменение теста',
                name: 'edittest',
                url: '/edit-test/:testId',
                reloadOnSearch: true,
                templateUrl: 'views/edittest/edittest.html',
                controller: 'EditTestCtrl'
            },
            {
                title: 'Просмотр теста',
                name: 'alltestspreview',
                url: '/all-tests/preview/:testId',
                templateUrl: 'views/alltests/preview/alltestspreview.html',
                controller: 'AllTestsPreviewCtrl'
            },
            {
                title: 'Просмотр теста',
                name: 'assignedtomepreview',
                url: '/assigned-to-me/preview/:testId',
                templateUrl: 'views/assignedtome/preview/assignedtomepreview.html',
                controller: 'AssignedToMePreviewCtrl'
            },
            {
                title: 'Просмотр теста',
                name: 'myresultspreview',
                url: '/my-results/preview/:testId',
                templateUrl: 'views/myresults/preview/myresultspreview.html',
                controller: 'MyResultsPreviewCtrl'
            },
            {
                title: 'Прохождение теста',
                name: 'testpassing',
                url: '/passing-test/:testId',
                templateUrl: 'views/testpassing/testpassing.html',
                controller: 'TestPassingCtrl'
            },
            {
                title: 'Просмотр теста',
                name: 'createdbymepreview',
                url: '/created-by-me/preview/:testId',
                templateUrl: 'views/createdbyme/preview/createdbymepreview.html',
                controller: 'CreatedByMePreviewCtrl'
            }
        ];

        for (i = states.length - 1; i >= 0; i--) {
            $stateProvider.state(states[i]);
        }

        $urlRouterProvider.otherwise(function ($injector) {
            var $state = $injector.get('$state');

            $state.go('404', {}, {location: 'replace'});
        });

        $locationProvider.html5Mode(true);
    });
