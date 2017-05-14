'use strict';
angular.module('app.mock',[])
    .factory('MockInterceptor', mockInterceptor)
    .config(function ($httpProvider) {
        $httpProvider.interceptors.push('MockInterceptor');
    });

function mockInterceptor() {
    return {
        'request': function (config) {
            if (config.url.indexOf('/tests/preview/1') >= 0 && config.params && (config.params.view === 'myResults')) {
                config.url = 'mockData/tests.myresults.preview.mock.json';
            } else if (config.url.indexOf('/tests/1') >= 0) {
                config.url = 'mockData/test.mock.json';
            } else if ((config.url.indexOf('/tests') >= 0) && config.params && (config.params.view === 'myResults')) {
                config.url = 'mockData/tests.all.mock.json';
            }

            return config;
        }
    };
}

angular.module('itest.portal').requires.push('app.mock');
