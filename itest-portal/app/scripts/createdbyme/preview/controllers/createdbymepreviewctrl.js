'use strict';

angular.module('itest.portal.createdbyme.preview.controllers')

    .controller('CreatedByMePreviewCtrl', function($scope, $stateParams, testService, $state) {
        var loadTestPreviewInfo = function () {
            var params = {
                view: 'createdByMe'
            };
            testService.getPreviewInfo($stateParams.testId, params).then(function (response) {
                $scope.test = response;
                loadTestResults();
            }, function () {

            });
        };

        var loadTestResults = function () {
            testService.getTestResults($stateParams.testId).then(function (response) {
                $scope.results = response.list;
                $scope.loadingTestData = false;
            }, function () {

            });
        };

        var getBackUrl = function() {
            return $state.href('createdbyme', {});
        };

        $scope.backUrl = getBackUrl();
        $scope.loadingTestData = true;
        loadTestPreviewInfo();
    });
