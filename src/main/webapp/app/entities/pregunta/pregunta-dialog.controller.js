(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('PreguntaDialogController', PreguntaDialogController);

    PreguntaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pregunta', 'PreguntaSeccion'];

    function PreguntaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pregunta, PreguntaSeccion) {
        var vm = this;

        vm.pregunta = entity;
        vm.clear = clear;
        vm.save = save;
        vm.preguntaseccions = PreguntaSeccion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pregunta.id !== null) {
                Pregunta.update(vm.pregunta, onSaveSuccess, onSaveError);
            } else {
                Pregunta.save(vm.pregunta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cumminsApp:preguntaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
