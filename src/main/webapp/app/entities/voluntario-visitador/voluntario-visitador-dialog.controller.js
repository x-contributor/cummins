(function () {
    'use strict';

    angular
            .module('cumminsApp')
            .controller('VoluntarioVisitadorDialogController', VoluntarioVisitadorDialogController);

    VoluntarioVisitadorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VoluntarioVisitador', 'Proyecto', 'User', '$translate'];

    function VoluntarioVisitadorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VoluntarioVisitador, Proyecto, User, $translate) {
        var vm = this;

        vm.voluntarioVisitador = entity;
        vm.clear = clear;
        vm.save = save;
        vm.proyectos = Proyecto.query();
        vm.user = {};
        vm.authorities = ['ROLE_VOLUNTARIO'];

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.voluntarioVisitador.id !== null) {
                VoluntarioVisitador.update(vm.voluntarioVisitador, onSaveSuccess, onSaveError);
            } else {
                crearUsuario();
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('cumminsApp:voluntarioVisitadorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        function onSaveSuccessUser(){
            VoluntarioVisitador.save(vm.voluntarioVisitador, onSaveSuccess, onSaveError);
        }
        
        function onSaveErrorUser(){
            vm.error = true;
        }
        
        function crearUsuario(){
            vm.user.langKey = $translate.use();
            vm.user.authorities = vm.authorities;
            vm.user.activated = true;
            vm.user.login = vm.user.email;
            vm.user.firstName = vm.voluntarioVisitador.nombre;
            vm.user.lastName = "";
            User.save(vm.user, onSaveSuccessUser, onSaveErrorUser);
        }

    }
})();
