(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pregunta', {
            parent: 'entity',
            url: '/pregunta',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cumminsApp.pregunta.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pregunta/preguntas.html',
                    controller: 'PreguntaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pregunta');
                    $translatePartialLoader.addPart('tipoPregunta');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pregunta-detail', {
            parent: 'entity',
            url: '/pregunta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cumminsApp.pregunta.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pregunta/pregunta-detail.html',
                    controller: 'PreguntaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pregunta');
                    $translatePartialLoader.addPart('tipoPregunta');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Pregunta', function($stateParams, Pregunta) {
                    return Pregunta.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('pregunta.new', {
            parent: 'pregunta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pregunta/pregunta-dialog.html',
                    controller: 'PreguntaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tipoPregunta: null,
                                consecutivo: null,
                                pregunta: null,
                                ayuda: null,
                                max: null,
                                min: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pregunta', null, { reload: true });
                }, function() {
                    $state.go('pregunta');
                });
            }]
        })
        .state('pregunta.edit', {
            parent: 'pregunta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pregunta/pregunta-dialog.html',
                    controller: 'PreguntaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pregunta', function(Pregunta) {
                            return Pregunta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pregunta', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pregunta.delete', {
            parent: 'pregunta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pregunta/pregunta-delete-dialog.html',
                    controller: 'PreguntaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pregunta', function(Pregunta) {
                            return Pregunta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pregunta', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
