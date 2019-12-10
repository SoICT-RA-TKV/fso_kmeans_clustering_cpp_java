#include <bits/stdc++.h>
#include "alglib/stdafx.h"
#include "alglib/dataanalysis.h"

using namespace std;
using namespace alglib;

void kmeans(clusterizerstate &s, kmeansreport &rep, real_2d_array &xy, int k, int num_of_tries, int num_of_iters) {
	clusterizercreate(s);
    clusterizersetpoints(s, xy, 2);
    clusterizersetkmeanslimits(s, num_of_tries, num_of_iters);
    clusterizerrunkmeans(s, k, rep);
}

double l2_norm(double x1, double y1, double x2, double y2) {
	return sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
}

bool check(kmeansreport &rep, double *a, double d) {
	for (int i = 0; i < rep.k; i++) {
		for (int j = 0; j < rep.npoints; j++) {
			if (rep.cidx[j] == i && l2_norm(rep.c[i][0], rep.c[i][1], a[2 * j], a[2 * j + 1]) > d) {
			return false;
			}
		}
	}
	return true;
}

int main(int argc, char** argv) {
	
	int n;
	int w, h;
	double d;
	
    clusterizerstate s;
    kmeansreport rep, res;
    real_2d_array xy;
    double *a;
    
    freopen("map.txt", "r", stdin);
	cin >> w >> h;
	cin >> n;
	cin >> d;
	cout << d << endl;
	a = (double *) malloc (2 * n * sizeof(double));
	for (int i = 0; i < n; i++) {
		cin >> a[2 * i] >> a[2 * i + 1];
	}
	xy.setcontent(n, 2, a);
	
	int num_of_tries = 20;
	int num_of_iters = 0;
	int num_of_kmeans_tries = 1;

	int first = 1;
	int last = n;
	int k;
	bool ok;
	
	while (first <= last) {
		ok = false;
		k = (first + last) / 2;
		for (int i = 0; i < num_of_kmeans_tries; i++) {
			kmeans(s, rep, xy, k, num_of_tries, num_of_iters);
			if (check(rep, a, d)) {
				swap(res, rep);
				ok = true;
				break;
			}
		}
		if (ok) {
			last = k - 1;
		} else {
			first = k + 1;
		}
	}
	cout << res.k << endl;
	
	freopen("result.txt", "w", stdout);
	if (int(res.terminationtype) == 1) {
		cout << res.npoints << endl;
		cout << res.nfeatures << endl;
		cout << res.k << endl;
		for (int i = 0; i < rep.k; i++) {
			for (int j = 0; j < rep.nfeatures; j++) {
				cout << rep.c[i][j] << ' ';
			}
			cout << endl;
		}
		for (int i = 0; i < rep.npoints; i++) {
			cout << rep.cidx[i] << ' ';
		}
		cout << endl;
	}
	
//	for (k = 1; k < n; k++) {
//		ok = false;
//		for (int i = 0; i < num_of_kmeans_tries; i++) {
//			kmeans(s, rep, xy, k, num_of_tries, num_of_iters);
//			if (check(rep, a, d)) {
//				swap(res, rep);
//				ok = true;
//				break;
//			}
//		}
//		if (ok) {
//			break;
//		}
//	}
//	cout << k << endl;
	
    return 0;
}



//	if (int(rep.terminationtype) == 1) {
//		cout << rep.npoints << endl;
//		cout << rep.nfeatures << endl;
//		cout << rep.k << endl;
//		for (int i = 0; i < rep.k; i++) {
//			for (int j = 0; j < rep.nfeatures; j++) {
//				cout << rep.c[i][j] << ' ';
//			}
//			cout << endl;
//		}
//		for (int i = 0; i < rep.npoints; i++) {
//			cout << rep.cidx[i] << ' ';
//		}
//		cout << endl;
//	}
