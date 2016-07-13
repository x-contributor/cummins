(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('proyecto-concurso', {
            parent: 'entity',
            url: '/proyecto-concurso',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'cumminsApp.proyectoConcurso.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/proyecto-concurso/proyecto-concursos.html',
                    controller: 'ProyectoConcursoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('proyectoConcurso');
                    $translatePartialLoader.addPart('estatusProyecto');
                    $translatePartialLoader.addPart('etapaProyecto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('proyecto-concurso-detail', {
            parent: 'entity',
            url: '/proyecto-concurso/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'cumminsApp.proyectoConcurso.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/proyecto-concurso/proyecto-concurso-detail.html',
                    controller: 'ProyectoConcursoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('proyectoConcurso');
                    $translatePartialLoader.addPart('estatusProyecto');
                    $translatePartialLoader.addPart('etapaProyecto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProyectoConcurso', function($stateParams, ProyectoConcurso) {
                    return ProyectoConcurso.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('proyecto-concurso.new', {
            parent: 'proyecto-concurso',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proyecto-concurso/proyecto-concurso-dialog.html',
                    controller: 'ProyectoConcursoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                estatus: null,
                                etapa: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('proyecto-concurso', null, { reload: true });
                }, function() {
                    $state.go('proyecto-concurso');
                });
            }]
        })
        .state('proyecto-concurso.edit', {
            parent: 'proyecto-concurso',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proyecto-concurso/proyecto-concurso-dialog.html',
                    controller: 'ProyectoConcursoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProyectoConcurso', function(ProyectoConcurso) {
                            return ProyectoConcurso.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('proyecto-concurso', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('proyecto-concurso.delete', {
            parent: 'proyecto-concurso',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proyecto-concurso/proyecto-concurso-delete-dialog.html',
                    controller: 'ProyectoConcursoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProyectoConcurso', function(ProyectoConcurso) {
                            return ProyectoConcurso.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('proyecto-concurso', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
