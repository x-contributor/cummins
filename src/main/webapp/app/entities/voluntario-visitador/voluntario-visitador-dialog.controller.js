(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('VoluntarioVisitadorDialogController', VoluntarioVisitadorDialogController);

    VoluntarioVisitadorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VoluntarioVisitador', 'Proyecto'];

    function VoluntarioVisitadorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VoluntarioVisitador, Proyecto) {
        var vm = this;

        vm.voluntarioVisitador = entity;
        vm.clear = clear;
        vm.save = save;
        vm.proyectos = Proyecto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.voluntarioVisitador.id !== null) {
                VoluntarioVisitador.update(vm.voluntarioVisitador, onSaveSuccess, onSaveError);
            } else {
                VoluntarioVisitador.save(vm.voluntarioVisitador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cumminsApp:voluntarioVisitadorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
