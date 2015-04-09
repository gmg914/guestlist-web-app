var coffeeApp = angular.module('guestListApp', ['ngResource']);

coffeeApp.controller('OrderController', function($scope, $resource) {
	
	$scope.addGuest = function() {
		var NewGuest = $resource( '/guestlist/guest/' );
		NewGuest.save($scope.guest);
	}
});