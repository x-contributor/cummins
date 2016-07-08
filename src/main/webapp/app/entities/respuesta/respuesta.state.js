(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('respuesta', {
            parent: 'entity',
            url: '/respuesta',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cumminsApp.respuesta.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/respuesta/respuestas.html',
                    controller: 'RespuestaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('respuesta');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('respuesta-detail', {
            parent: 'entity',
            url: '/respuesta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cumminsApp.respuesta.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/respuesta/respuesta-detail.html',
                    controller: 'RespuestaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('respuesta');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Respuesta', function($stateParams, Respuesta) {
                    return Respuesta.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('respuesta.new', {
            parent: 'respuesta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/respuesta/respuesta-dialog.html',
                    controller: 'RespuestaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                respuesta: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('respuesta', null, { reload: true });
                }, function() {
                    $state.go('respuesta');
                });
            }]
        })
        .state('respuesta.edit', {
            parent: 'respuesta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/respuesta/respuesta-dialog.html',
                    controller: 'RespuestaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Respuesta', function(Respuesta) {
                            return Respuesta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('respuesta', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('respuesta.delete', {
            parent: 'respuesta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/respuesta/respuesta-delete-dialog.html',
                    controller: 'RespuestaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Respuesta', function(Respuesta) {
                            return Respuesta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('respuesta', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
