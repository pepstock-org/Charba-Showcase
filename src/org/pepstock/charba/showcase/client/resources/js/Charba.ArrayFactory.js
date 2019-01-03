    function ArrayFactory() {};
    ArrayFactory.newArray = function() {
    	return new Array()
    }
    ArrayFactory.newObject = function() {
    	return new Object()
    }
  	ArrayFactory.thisInterceptor = function() {
    	var obj = new Object();
    	obj.caller = null;
    	obj.interceptor = function() {
    		console.log(JSON.stringify(arguments));
    		console.log(typeof obj.caller);
    		if (obj.caller != null && typeof obj.caller === 'function'){
//    			console.log(this);
    			console.log(typeof arg1);
//    			console.log(obj);
				var args = Array.of(this).concat(Array.prototype.slice.call(arguments));
				console.log("-----");
				console.log(args);
				console.log("-----");
				var result = obj.caller.apply(this, args);
				if (result === null){
					console.log("null");
				} else if (result === undefined){
					console.log("undefined");
				} else {
					console.log(result);
				}
    		} else {
    			console.log("No caller");
    		}
		};
		//obj.interceptor.call(this, 1,2,3,45);
    	return obj;
    }
    