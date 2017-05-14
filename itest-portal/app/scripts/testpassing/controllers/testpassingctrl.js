'use strict';

angular.module('itest.portal.testpassing.controllers')

    .controller('TestPassingCtrl', function ($scope, testService, $stateParams, answerService, orderDialog, notifier) {
        var matchAnswersToQuestions = function () {
            $.each($scope.answers, function (answerIndex, answer) {
                $.each($scope.test.questions, function (questionIndex, question) {
                    if (answer.questionId === question.id) {
                        $.each(question.answers, function (answerSecondIndex, answerSecond) {
                            if (answer.answerIds.indexOf(answerSecond.id) !== -1) {
                                answerSecond.selected = true;
                            }
                        });
                    }
                });
            });
        };

        var fillQuestionStatuses = function () {
            $.each($scope.test.questions, function (questionIndex, question) {
                var hasAnswer = false;
                $.each(question.answers, function (answerIndex, answer) {
                    if (answer.selected) {
                        hasAnswer = true;
                    }
                });
                $scope.questionStatuses.push(
                    {
                        questionId: question.id,
                        hasAnswer: hasAnswer
                    }
                );
            });
        };

        var getTest = function () {
            testService.getTestForPassing($stateParams.testId).then(function (response) {
                $scope.test = response;
                $scope.activeQuestion = $scope.test.questions[0];
                getExistingAnswers();
            }, function () {

            });
        };

        var getExistingAnswers = function () {
            answerService.getAnswers($stateParams.testId).then(function (response) {
                $scope.answers = response.list;
                matchAnswersToQuestions();
                fillQuestionStatuses();
                $scope.loading = false;
            }, function () {

            });
        };

        var getAnswers = function () {
            var results = [];
            $.each($scope.test.questions, function (questionIndex, question) {
                var result = {
                    questionId: question.id,
                    answerIds: []
                };
                $.each(question.answers, function (answerIndex, answer) {
                    if (answer.selected) {
                        result.answerIds.push(answer.id);
                    }
                });
                results.push(result);
            });
            return {records: results};
        };

        $scope.hasPreviousQuestion = function () {
            var result = true;
            $.each($scope.test.questions, function (questionIndex, question) {
                if (question.id === $scope.activeQuestion.id) {
                    result = questionIndex !== 0;
                }
            });
            return result;
        };

        $scope.hasNextQuestion = function () {
            var result = true;
            $.each($scope.test.questions, function (questionIndex, question) {
                if (question.id === $scope.activeQuestion.id) {
                    result = questionIndex !== ($scope.test.questions.length - 1);
                }
            });
            return result;
        };

        $scope.questionStatuses = [];

        $scope.loading = true;

        $scope.nextQuestion = function () {
            var index = 0;
            $.each($scope.test.questions, function (questionIndex, question) {
                if (question.id === $scope.activeQuestion.id) {
                    index = questionIndex;
                }
            });
            $scope.activeQuestion = $scope.test.questions[index + 1];
        };

        $scope.previousQuestion = function () {
            var index = 0;
            $.each($scope.test.questions, function (questionIndex, question) {
                if (question.id === $scope.activeQuestion.id) {
                    index = questionIndex;
                }
            });
            $scope.activeQuestion = $scope.test.questions[index - 1];
        };

        $scope.sendAnswer = function (questionId, answerId) {
            var hasAnswer = false;
            $.each($scope.test.questions, function (questionIndex, question) {
                if (question.id === questionId) {
                    $.each(question.answers, function (answerIndex, answer) {
                        if (answer.selected) {
                            hasAnswer = true;
                        }
                    });
                }
            });
            $.each($scope.questionStatuses, function (questionStatusIndex, questionStatus) {
                if (questionStatus.questionId === questionId) {
                    questionStatus.hasAnswer = hasAnswer;
                }
            });
            answerService.submitAnswer($scope.test.id, getAnswers()).then(function (response) {

            });
        };

        $scope.openCompleteDialog = function() {
            var header = 'Вы действительно хотите завершить тест?';
            var text = 'Вопросы оставленные без ответа будут засчитаны как отвеченные неправильно.';

            orderDialog.open($scope.test, header, text).then(function() {
                $scope.orderProcessing = true;

                testService.completeTest(getAnswers()).then(function () {
                    notifier.success('Тест был успешно завершен.');
                }).finally(function() {
                    $scope.orderProcessing = false;
                });
            });
        };

        getTest();
    });