(function() {
    'use strict';

    angular
        .module('cumminsApp')
        .controller('VoluntarioVisitadoProyectorController', VoluntarioVisitadoProyectorController);

    VoluntarioVisitadoProyectorController.$inject = ['$scope', '$http', 'VoluntarioVisitador'];

    function VoluntarioVisitadoProyectorController ($scope, $http, VoluntarioVisitador) {
        var vm = this;
        vm.proyectos = [];
        
        obtenerProyectos();

        function obtenerProyectos() {
            $http({
                method: 'GET',
                url: 'api/proyectos/voluntario'
            }).then(function successCallback(response) {
                vm.proyectos = response.data;
                console.log(response);
            }, function errorCallback(response) {
                console.log("ERROR");
            });
        }
    }
})();

