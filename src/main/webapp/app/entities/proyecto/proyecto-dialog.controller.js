(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ProyectoDialogController', ProyectoDialogController);

    ProyectoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Proyecto', 'Participante', 'VoluntarioVisitador'];

    function ProyectoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Proyecto, Participante, VoluntarioVisitador) {
        var vm = this;

        vm.proyecto = entity;
        vm.clear = clear;
        vm.save = save;
        vm.participantes = Participante.query();
        vm.voluntariovisitadors = VoluntarioVisitador.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.proyecto.id !== null) {
                Proyecto.update(vm.proyecto, onSaveSuccess, onSaveError);
            } else {
                Proyecto.save(vm.proyecto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cumminsApp:proyectoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
