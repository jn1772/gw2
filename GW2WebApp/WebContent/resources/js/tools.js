function getMoneyString(amount){
	var g, s, b;
	var a = amount;
	
	g = Math.trunc(a/10000);a = a%10000;
	s = Math.trunc(a/100);a = a%100;
	b = Math.trunc(a);
	
	return g+' <img src="resources/images/goldcoin.png"> '+
		   s+' <img src="resources/images/silvercoin.png"> '+
		   b+' <img src="resources/images/bronzecoin.png"> ';	
}

function getGold(a){
	var g;
	g = Math.trunc(a/10000);
	return g;
}

function getSilver(a){
	a = a % 10000;
	var s;
	s = Math.trunc(a/100);
	return s;
}

function getBronze(a){
	a = a % 100;
	var b = a;
	return b;
}

function getGoldString(a){
	var g = getGold(a);
	if(g != 0)	return g+' <img src="resources/images/goldcoin.png"> ';
	else return '';
}

function getSilverString(a, gPresent){
	var s = getSilver(a);
	var ret;
	if(s != 0 || gPresent === 1) return s+' <img src="resources/images/silvercoin.png"> ';
	else return '';
}

function getBronzeString(a, sPresent){
	var b = getBronze(a);
	if(b != 0 || sPresent === 1)	return b+' <img src="resources/images/bronzecoin.png"> ';
	else return '';
}

function getMoneyToolTip(a){
	var gs, ss, bs;
	gs = getGoldString(a);
	if(gs != '') ss = getSilverString(a, 1);
	else ss = getSilverString(a, 0);
	if(ss != '') bs = getBronzeString(a, 1);
	else bs = getBronzeString(a, 0);
	return gs+ss+bs;
}

function getMoneyYAxis(a){
	console.log("called");
	var gs, ss, bs;
	gs = getGoldString(a);
	ss = getSilverString(a, 0);
	bs = getBronzeString(a, 0);
	return gs+ss+bs;
}

