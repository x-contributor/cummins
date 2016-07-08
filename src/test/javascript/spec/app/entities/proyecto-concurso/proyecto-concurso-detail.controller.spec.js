'use strict';

describe('Controller Tests', function() {

    describe('ProyectoConcurso Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProyectoConcurso, MockProyecto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProyectoConcurso = jasmine.createSpy('MockProyectoConcurso');
            MockProyecto = jasmine.createSpy('MockProyecto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ProyectoConcurso': MockProyectoConcurso,
                'Proyecto': MockProyecto
            };
            createController = function() {
                $injector.get('$controller')("ProyectoConcursoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cumminsApp:proyectoConcursoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
