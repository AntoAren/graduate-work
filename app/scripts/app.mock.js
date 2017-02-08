'use strict';
angular.module('app.mock',[])
    .factory('MockInterceptor', mockInterceptor)
    .config(function ($httpProvider) {
        $httpProvider.interceptors.push('MockInterceptor');
    });

function mockInterceptor() {
    return {
        'request': function (config) {
            if (config.url.indexOf('/users') >= 0) {
                config.url = 'mockData/user.mock.json';
            }
            if (config.url.indexOf('/tokens/identity') >= 0) {
                config.url = 'mockData/token.mock.json';
                config.method = 'GET';
            }
            if (config.url.indexOf('/categories') >= 0) {
                config.url = 'mockData/categories.all.mock.json';
            }
            if (config.url.indexOf('/topics') >= 0) {
                config.url = 'mockData/topics.all.mock.json';
            }
            if (config.url.indexOf('/tests') >= 0) {
                config.url = 'mockData/tests.all.mock.json';
            }

            return config;
        }
    };
}

angular.module('itest.portal').requires.push('app.mock');
