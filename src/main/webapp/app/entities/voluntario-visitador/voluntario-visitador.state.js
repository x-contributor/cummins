(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('voluntario-visitador', {
            parent: 'entity',
            url: '/voluntario-visitador',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'cumminsApp.voluntarioVisitador.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/voluntario-visitador/voluntario-visitadors.html',
                    controller: 'VoluntarioVisitadorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('voluntarioVisitador');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('voluntario-visitador-detail', {
            parent: 'entity',
            url: '/voluntario-visitador/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'cumminsApp.voluntarioVisitador.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/voluntario-visitador/voluntario-visitador-detail.html',
                    controller: 'VoluntarioVisitadorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('voluntarioVisitador');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'VoluntarioVisitador', function($stateParams, VoluntarioVisitador) {
                    return VoluntarioVisitador.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('voluntario-visitador.new', {
            parent: 'voluntario-visitador',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/voluntario-visitador/voluntario-visitador-dialog.html',
                    controller: 'VoluntarioVisitadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                telefono: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('voluntario-visitador', null, { reload: true });
                }, function() {
                    $state.go('voluntario-visitador');
                });
            }]
        })
        .state('voluntario-visitador.edit', {
            parent: 'voluntario-visitador',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/voluntario-visitador/voluntario-visitador-dialog.html',
                    controller: 'VoluntarioVisitadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VoluntarioVisitador', function(VoluntarioVisitador) {
                            return VoluntarioVisitador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('voluntario-visitador', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('voluntario-visitador.delete', {
            parent: 'voluntario-visitador',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/voluntario-visitador/voluntario-visitador-delete-dialog.html',
                    controller: 'VoluntarioVisitadorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VoluntarioVisitador', function(VoluntarioVisitador) {
                            return VoluntarioVisitador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('voluntario-visitador', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('voluntario-visitador-proyecto', {
            parent: 'entity',
            url: '/voluntario-proyecto',
            data: {
                authorities: ['ROLE_ADMIN','ROLE_VOLUNTARIO'],
                pageTitle: 'cumminsApp.voluntarioVisitador.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/voluntario-visitador/voluntario-visitador-proyecto.html',
                    controller: 'VoluntarioVisitadoProyectorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('voluntarioVisitador');
                    $translatePartialLoader.addPart('global');
                    $translatePartialLoader.addPart('proyecto');
                    return $translate.refresh();
                }]
            }
        });
    }

})();
