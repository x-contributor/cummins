(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('ProyectoConcursoDialogController', ProyectoConcursoDialogController);

    ProyectoConcursoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ProyectoConcurso', 'Proyecto'];

    function ProyectoConcursoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, ProyectoConcurso, Proyecto) {
        var vm = this;

        vm.proyectoConcurso = entity;
        vm.clear = clear;
        vm.save = save;
        vm.proyectos = Proyecto.query({filter: 'proyectoconcurso-is-null'});
        $q.all([vm.proyectoConcurso.$promise, vm.proyectos.$promise]).then(function() {
            if (!vm.proyectoConcurso.proyecto || !vm.proyectoConcurso.proyecto.id) {
                return $q.reject();
            }
            return Proyecto.get({id : vm.proyectoConcurso.proyecto.id}).$promise;
        }).then(function(proyecto) {
            vm.proyectos.push(proyecto);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.proyectoConcurso.id !== null) {
                ProyectoConcurso.update(vm.proyectoConcurso, onSaveSuccess, onSaveError);
            } else {
                ProyectoConcurso.save(vm.proyectoConcurso, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cumminsApp:proyectoConcursoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
