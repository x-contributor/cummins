(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('participante-descripcion', {
            parent: 'entity',
            url: '/participante-descripcion',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'cumminsApp.participanteDescripcion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/participante-descripcion/participante-descripcions.html',
                    controller: 'ParticipanteDescripcionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('participanteDescripcion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('participante-descripcion-detail', {
            parent: 'entity',
            url: '/participante-descripcion/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'cumminsApp.participanteDescripcion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/participante-descripcion/participante-descripcion-detail.html',
                    controller: 'ParticipanteDescripcionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('participanteDescripcion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ParticipanteDescripcion', function($stateParams, ParticipanteDescripcion) {
                    return ParticipanteDescripcion.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('participante-descripcion.new', {
            parent: 'participante-descripcion',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/participante-descripcion/participante-descripcion-dialog.html',
                    controller: 'ParticipanteDescripcionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                misionVision: null,
                                aniosOperacion: null,
                                programasServcios: null,
                                resultadosLogros: null,
                                numeroBeneficiarios: null,
                                comentarios: null,
                                logotipo: null,
                                logotipoContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('participante-descripcion', null, { reload: true });
                }, function() {
                    $state.go('participante-descripcion');
                });
            }]
        })
        .state('participante-descripcion.edit', {
            parent: 'participante-descripcion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/participante-descripcion/participante-descripcion-dialog.html',
                    controller: 'ParticipanteDescripcionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ParticipanteDescripcion', function(ParticipanteDescripcion) {
                            return ParticipanteDescripcion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('participante-descripcion', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('participante-descripcion.delete', {
            parent: 'participante-descripcion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/participante-descripcion/participante-descripcion-delete-dialog.html',
                    controller: 'ParticipanteDescripcionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ParticipanteDescripcion', function(ParticipanteDescripcion) {
                            return ParticipanteDescripcion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('participante-descripcion', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
