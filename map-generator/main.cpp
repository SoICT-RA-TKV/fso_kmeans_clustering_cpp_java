#include <bits/stdc++.h>

using namespace std;

const int NMAX = 200;
const int RMAX = 1e3;

double frand(double _lb, double _ub) {
	double r = ((double) (rand() % RMAX)) / RMAX;
//	cout << r << endl;
	return _lb + r * (_ub - _lb);
}

struct Cluster {
	int x, y, r;
	double c;
	void read() {
		cin >> x >> y >> r >> c;
	};
	
	double l2_norm(int i, int j) {
		return (x - i) * (x - i) + (y - j) * (y - j);
	};
} cluster[NMAX];

double **prob, **dist;

struct ClusterMap {
	int w, h;
	int **cmap;
	int read() {
		cin >> w >> h;
		cout << w << ' ' << h << endl;
		cmap = (int **) malloc(h * sizeof(int *));
		prob = (double **) malloc(h * sizeof(double *));
		dist = (double **) malloc(h * sizeof(double *));
		for (int i = 0; i < h; i++) {
			cmap[i] = (int *) malloc (w * sizeof(int));
			prob[i] = (double *) malloc (w * sizeof(double));
			dist[i] = (double *) malloc (w * sizeof(double));
		}
	}
	
	void gen(Cluster *cluster, int n) {
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				prob[i][j] = 0;
				dist[i][j] = 0;
				for (int k = 0; k < n; k++) {
					dist[i][j] += cluster[k].l2_norm(i, j) / cluster[k].r;
				}
				for (int k = 0; k < n; k++) {
					prob[i][j] += cluster[k].c * (cluster[k].l2_norm(i, j) / cluster[k].r) / dist[i][j];
				}
				double p = frand(0, 1);
//				cout << p << endl;
				if (p < prob[i][j]) {
					cmap[i][j] = 1;
				} else {
					cmap[i][j] = 0;
				}
			}
		}
	}
	
	void write() {
		int cnt = 0;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				cnt += cmap[i][j];
			}
		}
		cout << cnt << endl;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				if (cmap[i][j] == 1) {
					cout << i + frand(0, 1) << ' ' << j + frand(0, 1) << endl;
				}
			}
		}
	}
} cluster_map;

int n;

int main(int argc, char** argv) {
	freopen("config.txt", "r", stdin);
	freopen("map.txt", "w", stdout);
	
	cin >> n;
//	cout << n << endl;
	for (int i = 0; i < n; i++) {
		cluster[i].read();
//		cout << cluster[i].r << endl;
	}
	cluster_map.read();
	cluster_map.gen(cluster, n);
	cluster_map.write();
	
	return 0;
}
