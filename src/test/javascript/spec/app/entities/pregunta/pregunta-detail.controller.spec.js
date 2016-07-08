'use strict';

describe('Controller Tests', function() {

    describe('Pregunta Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPregunta, MockPreguntaSeccion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPregunta = jasmine.createSpy('MockPregunta');
            MockPreguntaSeccion = jasmine.createSpy('MockPreguntaSeccion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Pregunta': MockPregunta,
                'PreguntaSeccion': MockPreguntaSeccion
            };
            createController = function() {
                $injector.get('$controller')("PreguntaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cumminsApp:preguntaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
