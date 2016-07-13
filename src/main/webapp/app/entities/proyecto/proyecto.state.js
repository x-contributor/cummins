(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('proyecto', {
            parent: 'entity',
            url: '/proyecto',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'cumminsApp.proyecto.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/proyecto/proyectos.html',
                    controller: 'ProyectoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('proyecto');
                    $translatePartialLoader.addPart('tematica');
                    $translatePartialLoader.addPart('tipoProyecto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('proyecto-detail', {
            parent: 'entity',
            url: '/proyecto/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'cumminsApp.proyecto.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/proyecto/proyecto-detail.html',
                    controller: 'ProyectoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('proyecto');
                    $translatePartialLoader.addPart('tematica');
                    $translatePartialLoader.addPart('tipoProyecto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Proyecto', function($stateParams, Proyecto) {
                    return Proyecto.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('proyecto.new', {
            parent: 'proyecto',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proyecto/proyecto-dialog.html',
                    controller: 'ProyectoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                proyecto: null,
                                tematica: null,
                                tipoProyectto: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('proyecto', null, { reload: true });
                }, function() {
                    $state.go('proyecto');
                });
            }]
        })
        .state('proyecto.edit', {
            parent: 'proyecto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proyecto/proyecto-dialog.html',
                    controller: 'ProyectoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Proyecto', function(Proyecto) {
                            return Proyecto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('proyecto', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('proyecto.delete', {
            parent: 'proyecto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proyecto/proyecto-delete-dialog.html',
                    controller: 'ProyectoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Proyecto', function(Proyecto) {
                            return Proyecto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('proyecto', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
