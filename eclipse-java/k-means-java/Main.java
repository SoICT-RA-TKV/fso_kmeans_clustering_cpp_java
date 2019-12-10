import java.util.*;
import java.text.*;
import java.io.*;

import pack.*;

public class Main {
	public static final int W = 50, H = 50; // ban do 50 x 50
  public static final int maxPoint = 128; // so luong max FSO /1 HAP
	public static final double maxRadius = 7.5; // ban kinh lon nhat cua HAP
  public static final int N_MAX = 2207;
  public static final int N = N_MAX - 7; // so luong FSO sinh ra
  public static final String finp = "INP2200.txt";
  public static final String fout = "OUT2200.txt";

	public static final double oo = (double) 1e9;
	public static final int INVALID = -1;
	public static final double EPS = 1e-9;
	public static int n,k;
	public static ToaDo[] tapDiem; // tapDiem[i] = diem thu i(0 <= i <= n-1)
	public static Cluster[] tapCluster;// tapCluster[i] = cluster thu i (0 <= i <= k-1)
	public static int[] mark = new int[N_MAX];
	public static int[][] w1 = new int[N_MAX][N_MAX];
	public static int[][] w2 = new int[N_MAX][N_MAX];

	public static void nhapDuLieu() throws IOException {
		Locale lc =new Locale("en","UN");
		Locale.setDefault(lc);
		File fi = new File("INP.txt");
		Scanner sc = new Scanner(fi);
		n = sc.nextInt();
		tapDiem = new ToaDo[n];
		for (int i=0;i < n;i++)
			tapDiem[i] = new ToaDo(sc.nextDouble(),sc.nextDouble());

		sc.close();
	}

	public static void chonK(int numOfCluster) {
		k = numOfCluster;
	}
	public static void xuatHinh() throws IOException{
		File f2 = new File("GRAPH.txt");
		FileWriter fw = new FileWriter(f2);

		for (ToaDo diem: tapDiem)
			fw.write(diem.toDecimalString() + "\n");
		fw.write("\n");

		for (int z=0;z < k;z++)
			for (ToaDo p: tapCluster[z].pointOfCluster) {
				fw.write(p.toLine(tapCluster[z].getCentroid()) + "\n");
			}
		fw.close();
	}

	public static void ghiOutput() throws IOException{
		File f2 = new File(fout);
		FileWriter fw = new FileWriter(f2);
		fw.write("#He so k la\n3.0\n");

		fw.write("#Number of Nodes\n"+ k + "\n");
		fw.write("# list of nodes\n#x #y #z\n");
		for (int i=0;i < k;i++) {
			fw.write(tapCluster[i].getCentroid().toDecimalString() + "\n");
			tapCluster[i].color(mark,i);
		}

		fw.write("#nguong BER\n0.001\n");
		fw.write("#Ma tran yeu cau D\n#source_index dest_index bw don vi Mbps\n");
		
		File f3 = new File(finp);
		FileWriter fw3 = new FileWriter(f3);
		fw3.write("#So luong nut la la\n" + n + "\n");
		fw3.write("#Toa do cac nut\n");
		for (int i=0;i < n;i++)
			fw3.write(tapDiem[i].toDecimalString() + "\n");
		fw3.write("#Ma tran yeu cau D\n#source_index dest_index bw don vi Mbps\n");
		
		for (int i=0;i < k;i++)
			for (int j=0;j < k;j++)
				w2[i][j] = 0;
		
		Random rd = new Random();
		for (int i=0;i < k;i++)
		for (Integer source: tapCluster[i].idPointOfCluster) {
			int destination = -1;
			while (true) {
				destination = rd.nextInt(n);
				if (mark[destination] != i) break;
			}
			if (destination == -1) System.out.println("BUGSS !");
			else {
				w1[source][destination] = rd.nextInt(1000) + 1;
				fw3.write((source+1) + " " + (destination+1) + " " + w1[source][destination] + "\n");
				w2[mark[source]][mark[destination]] += w1[source][destination];
			}
		}
		
		for (int i=0;i < k;i++)
		for (int j=0;j < k;j++)
			if (w2[i][j] > 0)
				fw.write((i+1) + " " + (j+1) + " " + w2[i][j] + "\n");
		
		fw3.close();
		fw.close();
	}

	public static void inThongTinCluster() throws IOException{

		for (int i=0;i < k;i++) {
			System.out.print("Cum thu " + (i+1) + " co " + tapCluster[i].getSize() + " FSOs ,");
			System.out.println("  MaxDis(HAP->FSO): " + tapCluster[i].maxDistance());
		}

	}
	public static void randomCum(){
		Random rd = new Random();
		boolean[] dd = new boolean[n];
		for (int i=0;i < n;i++)
			dd[i] = false;

		tapCluster = new Cluster[k];
		for (int i=0;i < k;i++) {
			int tmp = -1;
			while (true) {
				tmp = rd.nextInt(n);
				if (!dd[tmp]) break;
			}
			if (tmp == -1) {
				// k > n
				System.out.println("CAN'T RANDOM CLUSTER");
				return;
			}
			dd[tmp] = true;
			tapCluster[i] = new Cluster(tapDiem[tmp]);
		}

	}

	public static boolean phanCum(){

		double kCachMin;
		int nMark = 0;
		int indexOfCluster;
		int iterator = 0;
		for (int i=0;i < k;i++)
			mark[i] = -1;

		while (true) {

			for (int i=0;i < n;i++){
				//-----------------------------------------------
				// tim Cluster gan nhat voi diem i
				//-----------------------------------------------
				kCachMin = tapDiem[i].khoangCach(tapCluster[0].getCentroid());
				indexOfCluster = 0;

				for (int j=1;j < k;j++){
					double kCach = tapDiem[i].khoangCach(tapCluster[j].getCentroid());
					if (kCach < kCachMin){
						kCachMin = kCach;
						indexOfCluster = j;
					}
				}
				//----------------------------------------
				// them diem i vao Cluster gan nhat
				//---------------------------------------
				tapCluster[indexOfCluster].add(tapDiem[i]);
				tapCluster[indexOfCluster].addId(i);
				//-----------------------------------
				// phan phoi lai mot so FSO dam bao dieu kien rang buoc
				//-----------------------------------
				mark[indexOfCluster] = ++ nMark;

				while (tapCluster[indexOfCluster].getSize() > maxPoint) {
					int movePoint = -1;
					int destination = -1;
					double kcMin = 1e9;
					for (Integer p: tapCluster[indexOfCluster].idPointOfCluster)
						for (int z=0;z < k;z++)
							if (mark[z] != nMark)
								if (tapDiem[p].khoangCach(tapCluster[z].getCentroid()) < kcMin) {
									kcMin = tapDiem[p].khoangCach(tapCluster[z].getCentroid());
									movePoint = p;
									destination = z;
								}
					if (destination == -1) {
						return false;
					}
					else {
						mark[destination] = nMark;
						tapCluster[indexOfCluster].remove(tapDiem[movePoint]);
						tapCluster[indexOfCluster].removeId(movePoint);

						tapCluster[destination].add(tapDiem[movePoint]);
						tapCluster[destination].addId(movePoint);
						indexOfCluster = destination;
					}
				}
				//--------------------------------------

			}
			//------------------------------
			// kiem tra hoi tu
			//------------------------------
			boolean convergence = true;
			for (int z = 0;z < k;z++)
				if (!tapCluster[z].converged()) {
					convergence = false; 
					break;
				}
			if (convergence) break;
			//-----------------------------
			// dat lai cac centroid
			for (int i=0;i < k;i++){
				tapCluster[i] = new Cluster(tapCluster[i].center());
			}
			//-----------------------------
			// TH khong the hoi tu duoc thi thoat
			if (++ iterator > 1000) return false;
		}
		//in so vong da lap
		System.out.println("Iterator " + iterator);

		//------------------------------------------
		//kiem tra 2 dieu kien rang buoc
		//------------------------------------------
		for (int z=0;z < k;z++)
			if ((tapCluster[z].maxDistance() > maxRadius + EPS) || 
					(tapCluster[z].getSize() > maxPoint))
				return false;

		return true;
		//------------------------------------------
	}

	public static void randomInput() throws IOException{
		File ri = new File ("INP.txt");
		FileWriter fw = new FileWriter(ri);

		Random rd = new Random();
		int n = rd.nextInt(1) + N;
		fw.write(n + "\n");

		for (int i=0;i < n;i++) {
			double x = W * rd.nextDouble();
			double y = H * rd.nextDouble();
			fw.write(x + " " + y + "\n");
		}

		for (int i=0;i < n;i++) {
			for (int j=0;j < n;j++){
				int tmp = 0;
				if (i != j)
					tmp = rd.nextInt(1000);
				fw.write(tmp + " ");
			}
			fw.write("\n");
		}

		fw.close();

	}

	public static boolean isCanSolve(int numOfCluster,int soLanLap) {
		chonK(numOfCluster);
		int count = soLanLap;

		if (k * maxPoint < n) {
			return false;
		} else {
			for (count = 0;count < soLanLap;count ++) {
				randomCum();
				if (phanCum()) break;
			}
		}
		if (count >= soLanLap)
			return false;
		else
			return true;
	}
	public static int timSoCumNhoNhat() {
		int first = n / maxPoint;
		int last = n;
		while (first < last) {
			int mid = (first + last) /2;
			if (isCanSolve(mid,100)) last = mid;
			else
				first = mid+1;
		}
		return first;
	}
	public static void main(String[] args) throws IOException{
		long start = System.currentTimeMillis();
		randomInput();
		nhapDuLieu();
		
    int resultK;
    boolean binarySearch = true;

    if (binarySearch) {
      // tim so cum nho nhat bang tim kiem nhi phan
		  resultK = timSoCumNhoNhat();
      while (!isCanSolve(resultK,200)) resultK++;
    } else {
      // tim so cum nho nhat bang tim kiem tuan tu
      resultK = n / maxPoint;
		  while (!isCanSolve(resultK,200)) resultK++;
    }

		xuatHinh();
		ghiOutput();
		inThongTinCluster();
		long end = System.currentTimeMillis();
		System.out.println("Time Millis: " + (end - start));
	}
}