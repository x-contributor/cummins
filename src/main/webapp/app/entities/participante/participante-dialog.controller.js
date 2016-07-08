(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ParticipanteDialogController', ParticipanteDialogController);

    ParticipanteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Participante', 'Proyecto', 'Contacto', 'ParticipanteDescripcion'];

    function ParticipanteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Participante, Proyecto, Contacto, ParticipanteDescripcion) {
        var vm = this;

        vm.participante = entity;
        vm.clear = clear;
        vm.save = save;
        vm.proyectos = Proyecto.query();
        vm.contactos = Contacto.query();
        vm.participantedescripcions = ParticipanteDescripcion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.participante.id !== null) {
                Participante.update(vm.participante, onSaveSuccess, onSaveError);
            } else {
                Participante.save(vm.participante, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cumminsApp:participanteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
