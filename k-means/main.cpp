#include <bits/stdc++.h>
#include "alglib/stdafx.h"
#include "alglib/dataanalysis.h"

using namespace std;
using namespace alglib;

void kmeans(clusterizerstate &s, kmeansreport &rep, real_2d_array &xy, int k, int num_of_tries, int num_of_iters) {
	clusterizercreate(s);
    clusterizersetpoints(s, xy, 2);
    clusterizersetkmeanslimits(s, num_of_tries, num_of_iters);
    clusterizerrunkmeans(s, 2, rep);

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
}

double L2_norm(double x1, double y1, double x2, double y2) {
	
}

int main(int argc, char** argv) {
	
	int N = 5;
	int W;
	int D;
	
    clusterizerstate s;
    kmeansreport rep;
    real_2d_array xy = "[[1,1],[1,2],[4,1],[2,3],[4,1.5]]";
	
	int num_of_tries = 1;
	int num_of_iters = 0;
	int num_of_kmeans_tries = 5;
	
	int first = 1;
	int last = N;
	int k;
	
	while (first <= last) {
		k = (first + last) / 2;
		for (int i = 0; i < num_of_kmeans_tries; i++) {
			kmeans(s, rep, xy, k, num_of_tries, num_of_itres);
		}
	}
	
    return 0;
}
