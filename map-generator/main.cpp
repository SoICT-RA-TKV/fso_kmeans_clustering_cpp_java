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

double **prob, **dist, **tmpp;

struct ClusterMap {
	int w, h;
	int **cmap;
	int read() {
		cin >> w >> h;
		cout << w << ' ' << h << endl;
		cmap = (int **) malloc(h * sizeof(int *));
		prob = (double **) malloc(h * sizeof(double *));
		dist = (double **) malloc(h * sizeof(double *));
		tmpp = (double **) malloc(h * sizeof(double *));
		for (int i = 0; i < h; i++) {
			cmap[i] = (int *) malloc (w * sizeof(int));
			prob[i] = (double *) malloc (w * sizeof(double));
			dist[i] = (double *) malloc (w * sizeof(double));
			tmpp[i] = (double *) malloc(w * sizeof(double));
		}
	}
	
	void gen(Cluster *cluster, int n) {
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				prob[y][x] = 0;
				dist[y][x] = 0;
				tmpp[y][x] = 0;
				for (int k = 0; k < n; k++) {
					dist[y][x] += cluster[k].l2_norm(x, y);
				}
				for (int k = 0; k < n; k++) {
					tmpp[y][x] += dist[y][x] / cluster[k].l2_norm(x, y);
				}
				for (int k = 0; k < n; k++) {
					prob[y][x] += cluster[k].c * (dist[y][x] / cluster[k].l2_norm(x, y)) / tmpp[y][x];
				}
				double p = frand(0, 1);
//				cout << p << endl;
				if (p < prob[y][x]) {
					cmap[y][x] = 1;
				} else {
					cmap[y][x] = 0;
				}
			}
		}
	}
	
	void write() {
		int cnt = 0;
		
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				cnt += cmap[y][x];
			}
		}
		cout << cnt << endl;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if (cmap[y][x] == 1) {
					cout << x + frand(0, 1) << ' ' << y + frand(0, 1) << endl;
				}
			}
		}
	}
} cluster_map;

int n;

int main(int argc, char** argv) {
	freopen("config.txt", "r", stdin);
	freopen("maps.txt", "w", stdout);
	
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
