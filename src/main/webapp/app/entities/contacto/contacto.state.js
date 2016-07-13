(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('contacto', {
            parent: 'entity',
            url: '/contacto',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'cumminsApp.contacto.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contacto/contactos.html',
                    controller: 'ContactoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contacto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('contacto-detail', {
            parent: 'entity',
            url: '/contacto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cumminsApp.contacto.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contacto/contacto-detail.html',
                    controller: 'ContactoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contacto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Contacto', function($stateParams, Contacto) {
                    return Contacto.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('contacto.new', {
            parent: 'contacto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacto/contacto-dialog.html',
                    controller: 'ContactoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                email: null,
                                cargo: null,
                                direccion: null,
                                telefono: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('contacto', null, { reload: true });
                }, function() {
                    $state.go('contacto');
                });
            }]
        })
        .state('contacto.edit', {
            parent: 'contacto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacto/contacto-dialog.html',
                    controller: 'ContactoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Contacto', function(Contacto) {
                            return Contacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contacto', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contacto.delete', {
            parent: 'contacto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacto/contacto-delete-dialog.html',
                    controller: 'ContactoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Contacto', function(Contacto) {
                            return Contacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contacto', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
